export interface TokenDto {
    token: string
    expiration_date: number
}

export interface PostUsersTokenRequest {
    username: string
    password: string
}
