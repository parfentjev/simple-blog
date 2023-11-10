type PageDto<T> = {
  page: number
  totalPages: number
  items: T[]
}

export default PageDto
