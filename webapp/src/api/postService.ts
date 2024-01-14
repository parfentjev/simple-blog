import { PostDto, PostPreviewDto } from './models/post'
import api from './api'

export const getPosts = async () => {
    return api.get<PostPreviewDto[]>('/posts').then((response) => response.data)
}

export const getPost = async (id: string) => {
    return api.get<PostDto>(`/posts/${id}`).then((response) => response.data)
}
