import { getPostById } from '@/api/api-executor'
import PostDto from '@/api/models/PostDto'
import Post from '@/ui/post/Post'
import { GetServerSideProps } from 'next'
import { FC } from 'react'

const PostPage: FC<{ post: PostDto }> = ({ post }) => {
  return <Post post={post} />
}

export const getServerSideProps: GetServerSideProps<{
  post: PostDto
}> = async (context) => {
  const postId = context.params?.postId

  if (postId == undefined) {
    return { notFound: true }
  }

  const post = await getPostById(postId.toString())

  if (post.statusCode == 404) {
    return { notFound: true }
  }

  return { props: { post } }
}

export default PostPage
