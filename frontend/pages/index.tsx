import { getPosts } from '@/api/api-executor'
import Page from '@/api/models/Page'
import PostPreview from '@/api/models/PostPreview'
import PostListComponent from '@/ui/post/PostListComponent'
import { GetServerSideProps } from 'next'
import { FC } from 'react'

const PostListPage: FC<{ posts: Page<PostPreview> }> = ({ posts }) => {
  return <PostListComponent posts={posts.items} />
}

export const getServerSideProps: GetServerSideProps<{
  posts: Page<PostPreview>
}> = async () => {
  const posts = await getPosts().then((response) => response)

  return { props: { posts } }
}

export default PostListPage
