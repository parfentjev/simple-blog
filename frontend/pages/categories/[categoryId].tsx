import { FC, useCallback, useEffect, useState } from 'react'
import { LOAD_PAGE, LOAD_POSTS } from '..'
import { GetServerSideProps } from 'next'
import { getPosts } from '@/api/api-executor'
import PostList from '@/ui/post/PostList'
import Container from '@/ui/layout/Container'
import Button from '@/ui/layout/element/Button'
import { toast } from 'react-toastify'
import NotFoundErrorPage from '../404'
import GetPostsResponse from '@/api/models/response/GetPostsResponse'

type CategoryPageProps = {
  posts_page: GetPostsResponse
  categoryId: string
}

const CategoryPage: FC<CategoryPageProps> = ({ posts_page, categoryId }) => {
  const [currentPage, setCurrentPage] = useState(posts_page)
  const [items, setItems] = useState(posts_page.items)

  useEffect(() => {
    setItems(posts_page.items)
  }, [posts_page])

  const handleLoadMore = useCallback(async () => {
    const newPage = await getPosts(
      currentPage.page + 1,
      LOAD_POSTS,
      categoryId,
    ).then((response) => response)

    if (newPage.message) {
      toast.error(newPage.message)
    } else if (newPage.items.length < 1) {
      toast.error(`Couldn't load any posts for ${categoryId}`)
    } else {
      setCurrentPage(newPage)
      setItems((existingItems) => [...existingItems, ...newPage.items])
    }
  }, [currentPage, categoryId])

  if (posts_page.message || posts_page.items.length < 1) {
    toast.error(`Couldn't load any posts for ${categoryId}`)

    return <NotFoundErrorPage />
  }

  return (
    <>
      <PostList posts={items} />
      {currentPage.totalPages > currentPage.page && (
        <Container centered={true}>
          <Button onClick={handleLoadMore} text='Load more' />
        </Container>
      )}
    </>
  )
}

export const getServerSideProps: GetServerSideProps<CategoryPageProps> = async (
  context,
) => {
  const categoryId = context.params?.categoryId?.toString()

  if (categoryId == undefined) {
    return { notFound: true }
  }

  const posts_page = await getPosts(LOAD_PAGE, LOAD_POSTS, categoryId).then(
    (response) => response,
  )

  return { props: { posts_page, categoryId } }
}

export default CategoryPage
