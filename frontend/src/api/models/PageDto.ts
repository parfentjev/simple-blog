import ErrorMessageProvider from './response/ErrorMessageProvider'

type PageDto<T> = ErrorMessageProvider & {
  page: number
  totalPages: number
  items: T[]
}

export default PageDto
