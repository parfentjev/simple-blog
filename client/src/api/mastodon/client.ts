class MastodonClient {
    baseUrl: string

    constructor(baseUrl: string) {
        this.baseUrl = baseUrl.startsWith('https://')
            ? baseUrl
            : `https://${baseUrl}`
    }

    getInstance = async (): Promise<boolean> => {
        return await fetch(`${this.baseUrl}/api/v2/instance`)
            .then((response) => response.json())
            .then((data) => {
                if (data.version.length > 0) {
                    return true
                }

                return false
            })
            .catch(() => false)
    }
}

export default MastodonClient
