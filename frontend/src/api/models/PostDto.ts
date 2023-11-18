import CategoryDto from './CategoryDto'

type PostDto = {
  id?: string
  title: string
  summary: string
  text: string
  date: string
  visible: boolean
  categories: CategoryDto[]
}

export default PostDto
