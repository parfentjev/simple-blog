import { getPostById } from '@/api/api-executor'
import PostDto from '@/api/models/PostDto'
import Post from '@/ui/post/Post'
import { GetServerSideProps } from 'next'
import { FC } from 'react'
import { toast } from 'react-toastify'
import NotFoundErrorPage from '../../404'

const PostPage: FC<{ post: PostDto }> = ({ post }) => {
  if (post.message) {
    toast.error(post.message)

    return <NotFoundErrorPage />
  }

  return <Post post={post} />
}

export const getServerSideProps: GetServerSideProps<{
  post?: PostDto
}> = async (context) => {
  const postId = context.params?.postId

  if (postId == undefined) {
    return { notFound: true }
  }

  const post = await getPostById(postId.toString())

  return { props: { post } }
}

export default PostPage
