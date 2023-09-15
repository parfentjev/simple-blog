import axios from 'axios'
import { ApiEndpoints, endpoint } from './api-ednpoints'
import PostPreview from './models/PostPreview'
import Page from './models/Page'
import Post from './models/Post'

export const getPosts = async () => {
  return await axios
    .get<Page<PostPreview>>(endpoint(ApiEndpoints.POSTS))
    .then((response) => response.data)
}

export const getPostById = async (postId: string) => {
  return axios
    .get<Post>(endpoint(ApiEndpoints.POSTS_ID, postId))
    .then((response) => response.data)
}
