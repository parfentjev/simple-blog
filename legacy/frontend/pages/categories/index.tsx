import { getCategories } from '@/api/api-executor'
import GetCategoriesResponse from '@/api/models/response/GetCategoriesResponse'
import Categories from '@/ui/category/Categories'
import { GetServerSideProps } from 'next'
import { FC } from 'react'
import InternalServerErrorPage from '../500'
import { toast } from 'react-toastify'

type CategoriesPageProps = {
  categories: GetCategoriesResponse
}

const CategoriesPage: FC<CategoriesPageProps> = ({ categories }) => {
  if (categories.message) {
    toast.error(categories.message)

    return <InternalServerErrorPage />
  }

  return <Categories categories={categories} />
}

export const getServerSideProps: GetServerSideProps<
  CategoriesPageProps
> = async () => {
  const categories = await getCategories()

  return { props: { categories } }
}

export default CategoriesPage
