const baseUrl = process.env.REACT_APP_SERVICE_URL

export enum ApiEndpoints {
    POSTS = '/posts',
    POSTS_ID = '/posts/{0}'
}

export const endpoint = (endpoint: ApiEndpoints, ...args: any[]) =>
    baseUrl +
    endpoint.replace(/{(\d+)}/g, (match, index) => {
        return typeof args[index] !== 'undefined' ? String(args[index]) : match
    })