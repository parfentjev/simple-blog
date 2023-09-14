import axios from 'axios'
import { ApiEndpoints, endpoint } from './api-ednpoints'
import PostPreview from './models/PostPreview'
import Page from './models/Page'
import Post from './models/Post'

export const getPosts = async () => {
    return axios.get(endpoint(ApiEndpoints.POSTS)).then((response) => new Page<PostPreview>(response.data))
}

export const getPostById = async (postId: string) => {
    return axios.get(endpoint(ApiEndpoints.POSTS_ID, postId)).then((response) => new Post(response.data))
}