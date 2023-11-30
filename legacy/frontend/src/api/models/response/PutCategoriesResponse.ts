import CategoryDto from '../CategoryDto'
import ErrorMessageProvider from './ErrorMessageProvider'

type PutCategoriesResponse = CategoryDto & ErrorMessageProvider

export default PutCategoriesResponse
