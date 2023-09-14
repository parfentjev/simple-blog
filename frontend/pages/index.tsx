import { getPosts } from '@/api/api-executor'
import Page from '@/api/models/Page'
import PostPreview from '@/api/models/PostPreview'
import PostListComponent from '@/ui/components/post/PostListComponent'
import { FC, useEffect, useState } from 'react'

const PostListPage: FC = () => {
  const [page, setPage] = useState<Page<PostPreview>>()

  useEffect(() => {
    getPosts().then((response) => setPage(response))
  }, [])

  return (
    (page && <PostListComponent posts={page?.items} />) || <p>loading...</p>
  )
}

export default PostListPage
