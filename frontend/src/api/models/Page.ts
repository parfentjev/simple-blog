class Page<T> {
    page: number
    totalPages: number
    items: T[]

    constructor(data: Page<T>) {
        this.page = data.page
        this.totalPages = data.totalPages
        this.items = data.items
    }
}

export default Page