import CategoryDto from './CategoryDto'

type PostPreviewDto = {
  id: string
  title: string
  summary: string
  date: string
  categories: CategoryDto[]
}

export default PostPreviewDto
