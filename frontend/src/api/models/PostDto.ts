import ErrorMessageProvider from "./response/ErrorMessageProvider"

type PostDto = ErrorMessageProvider & {
  id?: string
  title: string
  summary: string
  text: string
  date: string
  visible: boolean
  category: string[]
}

export default PostDto
