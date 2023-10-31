import axios from 'axios'
import { ApiEndpoints, endpoint } from './api-ednpoints'
import PostPreviewDto from './models/PostPreviewDto'
import PageDto from './models/PageDto'
import PostDto from './models/PostDto'
import PostUsersTokenRequest from './models/request/PostUsersTokenRequest'
import PostUsersTokenResponse from './models/response/PostUsersTokenResponse'
import ErrorMessageProvider from './models/response/ErrorMessageProvider'

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

export const postUsersToken = async (data: PostUsersTokenRequest) => {
  return axios
    .post<PostUsersTokenResponse>(endpoint(ApiEndpoints.USERS_TOKEN), data)
    .then((response) => response.data)
    .catch((error) => handleError<PostUsersTokenResponse>(error))
}

const handleError = <T extends ErrorMessageProvider>(error: any): T => {
  const message = error.response.data.message

  return {
    message: message ? message : 'Request has failed.',
    statusCode: error.response.statusCode,
  } as T
}
