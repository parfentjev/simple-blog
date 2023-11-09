import PostPreviewDto from '@/api/models/PostPreviewDto'
import { FC, useCallback, useEffect, useState } from 'react'
import { LOAD_PAGE, LOAD_POSTS } from '..'
import { GetServerSideProps } from 'next'
import PageDto from '@/api/models/PageDto'
import { getPosts } from '@/api/api-executor'
import PostList from '@/ui/post/PostList'
import Container from '@/ui/layout/Container'
import Button from '@/ui/layout/element/Button'
import { toast } from 'react-toastify'
import NotFoundErrorPage from '../404'

const CategoryPage: FC<{
  posts_page: PageDto<PostPreviewDto>
  categoryName: string
}> = ({ posts_page, categoryName }) => {
  const [currentPage, setCurrentPage] = useState(posts_page)
  const [items, setItems] = useState(posts_page.items)

  useEffect(() => {
    setItems(posts_page.items)
  }, [posts_page])

  const handleLoadMore = useCallback(async () => {
    const newPage = await getPosts(
      currentPage.page + 1,
      LOAD_POSTS,
      categoryName,
    ).then((response) => response)

    if (newPage.message) {
      toast.error(newPage.message)
    } else if (newPage.items.length < 1) {
      toast.error(`Couldn't load any posts for ${categoryName}`)
    } else {
      setCurrentPage(newPage)
      setItems((existingItems) => [...existingItems, ...newPage.items])
    }
  }, [currentPage, categoryName])

  if (posts_page.message || posts_page.items.length < 1) {
    toast.error(`Couldn't load any posts for ${categoryName}`)

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

export const getServerSideProps: GetServerSideProps<{
  posts_page: PageDto<PostPreviewDto>
  categoryName: string
}> = async (context) => {
  const categoryName = context.params?.categoryName?.toString()

  if (categoryName == undefined) {
    return { notFound: true }
  }

  const posts_page = await getPosts(LOAD_PAGE, LOAD_POSTS, categoryName).then(
    (response) => response,
  )

  return { props: { posts_page, categoryName } }
}

export default CategoryPage
