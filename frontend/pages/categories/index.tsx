import { getCategories } from '@/api/api-executor'
import GetCategoriesResponse from '@/api/models/response/GetCategoriesResponse'
import Categories from '@/ui/category/Categories'
import { GetServerSideProps } from 'next'
import { FC } from 'react'
import InternalServerErrorPage from '../500'
import { toast } from 'react-toastify'

const CategoriesPage: FC<{ categories: GetCategoriesResponse }> = ({
  categories,
}) => {
  if (categories.message) {
    toast.error(categories.message)

    return <InternalServerErrorPage />
  }

  return <Categories categories={categories} />
}

export const getServerSideProps: GetServerSideProps<{
  categories: GetCategoriesResponse
}> = async () => {
  const categories = await getCategories()

  return { props: { categories } }
}

export default CategoriesPage
