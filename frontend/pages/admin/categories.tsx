import { getCategories } from '@/api/api-executor'
import CategoryDto from '@/api/models/CategoryDto'
import { ProtectedRoute } from '@/store/auth-context'
import EditCategories from '@/ui/category/EditCategories'
import Container from '@/ui/layout/Container'
import { FC, useEffect, useState } from 'react'
import { toast } from 'react-toastify'

const EditCategoriesPage: FC = () => {
  const [categories, setCategories] = useState<CategoryDto[]>([])

  useEffect(() => {
    getCategories().then((response) => {
      response.message ? toast.error(response.message) : setCategories(response)
    })
  }, [])

  return (
    <ProtectedRoute>
      <Container name='Manage categories' centered={true}>
        <EditCategories categories={categories} />
      </Container>
    </ProtectedRoute>
  )
}

export default EditCategoriesPage
