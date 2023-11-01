import axios from 'axios'
import { ApiEndpoints, endpoint } from './api-ednpoints'
import PostPreviewDto from './models/PostPreviewDto'
import PageDto from './models/PageDto'
import PostDto from './models/PostDto'
import PostUsersTokenRequest from './models/request/PostUsersTokenRequest'
import PostUsersTokenResponse from './models/response/PostUsersTokenResponse'
import ErrorMessageProvider from './models/response/ErrorMessageProvider'
import PostPostsReponse from './models/response/PostPostsResponse'
import TokenDto from './models/TokenDto'

export const getPosts = async (page: number, size: number) => {
  return axios
    .get<PageDto<PostPreviewDto>>(endpoint(ApiEndpoints.POSTS), {
      params: {
        page,
        size,
      },
    })
    .then((response) => response.data)
}

export const getPostById = async (postId: string) => {
  return axios
    .get<PostDto>(endpoint(ApiEndpoints.POSTS_ID, postId))
    .then((response) => response.data)
}

export const getPostByIdWithToken = async (token: TokenDto, postId: string) => {
  return axios
    .get<PostDto>(endpoint(ApiEndpoints.POSTS_ID, postId), requestConfig(token))
    .then((response) => response.data)
}

export const postUsersToken = async (data: PostUsersTokenRequest) => {
  return axios
    .post<PostUsersTokenResponse>(endpoint(ApiEndpoints.USERS_TOKEN), data)
    .then((response) => response.data)
    .catch((error) => handleError<PostUsersTokenResponse>(error))
}

export const postPosts = async (token: TokenDto, data: PostDto) => {
  return axios
    .post<PostPostsReponse>(
      endpoint(ApiEndpoints.POSTS),
      data,
      requestConfig(token),
    )
    .then((response) => response.data)
    .catch((error) => handleError<PostPostsReponse>(error))
}

export const putPosts = async (token: TokenDto, data: PostDto) => {
  return axios
    .put<PostPostsReponse>(
      endpoint(ApiEndpoints.POSTS_ID, data.id),
      data,
      requestConfig(token),
    )
    .then((response) => response.data)
    .catch((error) => handleError<PostPostsReponse>(error))
}

const handleError = <T extends ErrorMessageProvider>(error: any): T => {
  const response = error.response
  const message = response ? response.data.message : null
  const statusCode = response ? response.statusCode : null

  return {
    message: message ? message : 'Request has failed.',
    statusCode,
  } as T
}

const requestConfig = (token: TokenDto) => {
  return {
    headers: {
      Authorization: `Bearer ${token.token}`,
    },
  }
}
