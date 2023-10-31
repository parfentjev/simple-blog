import axios from 'axios'
import { ApiEndpoints, endpoint } from './api-ednpoints'
import PostPreviewDto from './models/PostPreviewDto'
import PageDto from './models/PageDto'
import PostDto from './models/PostDto'

export const getPosts = async (page: number, size: number) => {
  return await axios
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
