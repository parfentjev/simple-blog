type Page<T> = {
  page: number
  totalPages: number
  items: T[]
}

export default Page
