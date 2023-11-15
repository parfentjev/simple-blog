const baseUrl = process.env.SERVICE_URL || process.env.NEXT_PUBLIC_SERVICE_URL

export enum ApiEndpoints {
  POSTS = '/posts',
  POSTS_ID = '/posts/{0}',
  CATEGORIES = '/categories',
  CATEGORIES_ID = '/categories/{0}',
  USERS_TOKEN = '/users/token',
}

export const endpoint = (endpoint: ApiEndpoints, ...args: any[]) =>
  baseUrl +
  endpoint.replace(/{(\d+)}/g, (match, index) => {
    return typeof args[index] !== 'undefined' ? String(args[index]) : match
  })
