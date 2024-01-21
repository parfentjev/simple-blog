import { PostDto, PostPreviewDto } from './models/post'
import { api, authApi } from './api'
import { TokenDto } from './models/user'

export const getPostsPublished = async () => {
    return api()
        .get<PostPreviewDto[]>('/posts/published')
        .then((response) => response.data)
}

export const getPublishedPostsById = async (id: string) => {
    return api()
        .get<PostDto>(`/posts/published/${id}`)
        .then((response) => response.data)
}

export const getPostsEditorById = async (token: TokenDto, id: string) => {
    return authApi(token)
        .get<PostDto>(`/posts/editor/${id}`)
        .then((response) => response.data)
}

export const postPostsEditor = async (token: TokenDto, post: PostDto) => {
    return authApi(token)
        .post<PostDto>(`/posts/editor`, post)
        .then((response) => response.data)
}

export const putPostsEditorById = async (
    token: TokenDto,
    id: string,
    post: PostDto
) => {
    return authApi(token)
        .put<PostDto>(`/posts/editor/${id}`, post)
        .then((response) => response.data)
}
