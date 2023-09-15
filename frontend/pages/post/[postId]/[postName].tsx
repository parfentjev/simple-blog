import { getPostById } from '@/api/api-executor'
import Post from '@/api/models/Post'
import { PostComponent } from '@/ui/post/PostComponent'
import { GetServerSideProps } from 'next'
import { FC } from 'react'

const PostPage: FC<{ post: Post }> = ({ post }) => {
  return <PostComponent post={post} />
}

export const getServerSideProps: GetServerSideProps<{
  post: Post
}> = async (context) => {
  const postId = context.params?.postId

  if (postId == undefined) {
    return { notFound: true }
  }

  const post = await getPostById(postId.toString())

  return { props: { post } }
}

export default PostPage
