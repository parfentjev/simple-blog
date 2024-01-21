export interface TokenDto {
    token: string
    expirationDate: number
}

export interface PostUsersTokenRequest {
    username: string
    password: string
}
