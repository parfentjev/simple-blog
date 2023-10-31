import { getPosts } from '@/api/api-executor'
import PageDto from '@/api/models/PageDto'
import PostPreviewDto from '@/api/models/PostPreviewDto'
import LoadMoreButton from '@/ui/post/LoadMoreButton'
import PostList from '@/ui/post/PostList'
import { GetServerSideProps } from 'next'
import { FC, useCallback, useState } from 'react'

const LOAD_PAGE = 1
const LOAD_POSTS = 20

const PostListPage: FC<{ posts_page: PageDto<PostPreviewDto> }> = ({
  posts_page,
}) => {
  const [currentPage, setCurrentPage] = useState(posts_page)
  const [items, setItems] = useState(posts_page.items)

  const handleLoadMore = useCallback(async () => {
    const newPage = await getPosts(currentPage.page + 1, LOAD_POSTS).then(
      (response) => response,
    )

    setCurrentPage(newPage)
    setItems((existingItems) => [...existingItems, ...newPage.items])
  }, [currentPage])

  return (
    <>
      <PostList posts={items} />
      {currentPage.totalPages > currentPage.page && (
        <LoadMoreButton onClick={handleLoadMore} />
      )}
    </>
  )
}

export const getServerSideProps: GetServerSideProps<{
  posts_page: PageDto<PostPreviewDto>
}> = async () => {
  const posts_page = await getPosts(LOAD_PAGE, LOAD_POSTS).then(
    (response) => response,
  )

  return { props: { posts_page } }
}

export default PostListPage
