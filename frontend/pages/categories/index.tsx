import { getCategories } from '@/api/api-executor'
import GetCategoriesResponse from '@/api/models/response/GetCategoriesResponse'
import Categories from '@/ui/category/Categories'
import { GetServerSideProps } from 'next'
import { FC } from 'react'

const CategoriesPage: FC<{ categories: GetCategoriesResponse }> = ({
  categories,
}) => {
  return <Categories categories={categories} />
}

export const getServerSideProps: GetServerSideProps<{
  categories: GetCategoriesResponse
}> = async () => {
  const categories = await getCategories()

  return { props: { categories } }
}

export default CategoriesPage
