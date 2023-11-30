import { getPosts } from '@/api/api-executor'
import Container from '@/ui/layout/Container'
import Button from '@/ui/layout/element/Button'
import PostList from '@/ui/post/PostList'
import { GetServerSideProps } from 'next'
import { FC, useCallback, useState } from 'react'
import { toast } from 'react-toastify'
import InternalServerErrorPage from './500'
import GetPostsResponse from '@/api/models/response/GetPostsResponse'

export const LOAD_PAGE = 1
export const LOAD_POSTS = 20

type PostListPageProps = {
  posts_page: GetPostsResponse
}

const PostListPage: FC<PostListPageProps> = ({ posts_page }) => {
  const [currentPage, setCurrentPage] = useState(posts_page)
  const [items, setItems] = useState(posts_page.items)

  const handleLoadMore = useCallback(async () => {
    const newPage = await getPosts(currentPage.page + 1, LOAD_POSTS).then(
      (response) => response,
    )

    setCurrentPage(newPage)
    setItems((existingItems) => [...existingItems, ...newPage.items])
  }, [currentPage])

  if (posts_page.message) {
    toast.error(posts_page.message)

    return <InternalServerErrorPage />
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

export const getServerSideProps: GetServerSideProps<
  PostListPageProps
> = async () => {
  const posts_page = await getPosts(LOAD_PAGE, LOAD_POSTS).then(
    (response) => response,
  )

  return { props: { posts_page } }
}

export default PostListPage
