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
import GetCategoriesResponse from './models/response/GetCategoriesResponse'
import GetPostsResponse from './models/response/GetPostsResponse'
import GetPostByIdResponse from './models/response/GetPostByIdResponse'
import PostCategoriesRequest from './models/request/PostCategoriesRequest'
import PostCategoriesResponse from './models/response/PostCategoriesResponse'
import PutCategoriesRequest from './models/request/PutCategoriesRequest'
import PutCategoriesResponse from './models/response/PutCategoriesResponse'

export const getPosts = async (
  page: number,
  size: number,
  categoryId?: string,
) => {
  return axios
    .get<GetPostsResponse>(endpoint(ApiEndpoints.POSTS), {
      params: {
        page,
        size,
        categoryId,
      },
    })
    .then((response) => response.data)
    .catch((error) => handleError<GetPostsResponse>(error))
}

export const getPostById = async (postId: string) => {
  return axios
    .get<GetPostByIdResponse>(endpoint(ApiEndpoints.POSTS_ID, postId))
    .then((response) => response.data)
    .catch((error) => handleError<GetPostByIdResponse>(error))
}

export const getPostByIdWithToken = async (token: TokenDto, postId: string) => {
  return axios
    .get<GetPostByIdResponse>(
      endpoint(ApiEndpoints.POSTS_ID, postId),
      requestConfig(token),
    )
    .then((response) => response.data)
    .catch((error) => handleError<GetPostByIdResponse>(error))
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

export const getCategories = async () => {
  return axios
    .get<GetCategoriesResponse>(endpoint(ApiEndpoints.CATEGORIES))
    .then((response) => response.data)
    .catch((error) => handleError<GetCategoriesResponse>(error))
}

export const postCategories = async (
  data: PostCategoriesRequest,
  token: TokenDto,
) => {
  return axios
    .post<PostCategoriesResponse>(
      endpoint(ApiEndpoints.CATEGORIES),
      data,
      requestConfig(token),
    )
    .then((response) => response.data)
    .catch((error) => handleError<PostCategoriesResponse>(error))
}

export const putCategories = async (
  categoryId: string,
  data: PutCategoriesRequest,
  token: TokenDto,
) => {
  return axios
    .put<PutCategoriesResponse>(
      endpoint(ApiEndpoints.CATEGORIES_ID, categoryId),
      data,
      requestConfig(token),
    )
    .then((response) => response.data)
    .catch((error) => handleError(error))
}

export const deleteCategories = async (categoryId: string, token: TokenDto) => {
  return axios
    .delete<ErrorMessageProvider>(
      endpoint(ApiEndpoints.CATEGORIES_ID, categoryId),
      requestConfig(token),
    )
    .catch((error) => handleError(error))
}

const handleError = <T extends ErrorMessageProvider>(error: any): T => {
  const response = error.response
  const message = response ? response.data.message : null
  const statusCode = response ? response.status : null

  console.log(response)

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
