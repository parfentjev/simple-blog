import CategoryDto from '@/api/models/CategoryDto'
import {
  ChangeEvent,
  FC,
  FormEvent,
  useCallback,
  useEffect,
  useState,
} from 'react'
import Button from '../layout/element/Button'
import {
  deleteCategories,
  postCategories,
  putCategories,
} from '@/api/api-executor'
import PutCategoriesRequest from '@/api/models/request/PutCategoriesRequest'
import { useAuthContext } from '@/store/auth-context'
import { toast } from 'react-toastify'

type EditCategoriesType = {
  categories: CategoryDto[]
}

const EditCategories: FC<EditCategoriesType> = ({ categories }) => {
  const { token } = useAuthContext()
  const [categoriesState, setCategoriesState] = useState<CategoryDto[]>([])

  useEffect(() => {
    setCategoriesState(categories)
  }, [categories])

  const handleNewButton = useCallback(() => {
    setCategoriesState([...categoriesState, { name: '' }])
  }, [categoriesState])

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault()

    let errors: string[] = []

    categoriesState.forEach((c) => {
      if (!token) {
        toast.error('Token is not defined.')

        return
      }

      const request: PutCategoriesRequest = { name: c.name }

      if (c.id && c.name.trim().length > 0) {
        putCategories(c.id, request, token).then((response) => {
          if (response.message) {
            errors.push(response.message)
          }
        })
      } else if (c.id && c.name.trim().length === 0) {
        deleteCategories(c.id, token)
      } else if (c.name.trim().length > 0) {
        postCategories(request, token).then((response) => {
          if (response.message) {
            errors.push(response.message)
          }
        })
      }
    })

    if (errors.length > 0) {
      errors.forEach((e) => toast.error(e))
    } else {
      toast.success('Saved.')
    }
  }

  const handleChange = (i: number, e: ChangeEvent<HTMLInputElement>) => {
    let data = [...categoriesState]
    data[i] = { id: e.target.id, name: e.target.value }
    setCategoriesState(data)
  }

  return (
    <form onSubmit={handleSubmit}>
      {categoriesState.map((c, i) => (
        <div key={i}>
          <input
            id={c.id}
            type='text'
            value={c.name}
            placeholder='category name'
            onChange={(e) => handleChange(i, e)}
          />
        </div>
      ))}
      <p>To remove a category, erase its name and save.</p>
      <Button text='New' onClick={handleNewButton} />{' '}
      <Button text='Save' type='submit' />
    </form>
  )
}

export default EditCategories
