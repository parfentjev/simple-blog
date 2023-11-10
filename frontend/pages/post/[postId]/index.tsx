import { getPostById } from '@/api/api-executor'
import Post from '@/ui/post/Post'
import {
  GetServerSideProps,
  GetServerSidePropsContext,
  PreviewData,
} from 'next'
import { FC } from 'react'
import { toast } from 'react-toastify'
import NotFoundErrorPage from '../../404'
import GetPostByIdResponse from '@/api/models/response/GetPostByIdResponse'
import { ParsedUrlQuery } from 'querystring'

type PostPageProps = {
  post: GetPostByIdResponse
}

const PostPage: FC<PostPageProps> = ({ post }) => {
  if (post.message) {
    toast.error(post.message)

    return <NotFoundErrorPage />
  }

  return <Post post={post} />
}

export const loadPost: GetServerSideProps<PostPageProps> = async (
  context: GetServerSidePropsContext<ParsedUrlQuery, PreviewData>,
) => {
  const postId = context.params?.postId

  if (postId == undefined) {
    return { notFound: true }
  }

  const post = await getPostById(postId.toString())

  return { props: { post } }
}

export const getServerSideProps: GetServerSideProps<PostPageProps> = async (
  context,
) => {
  return await loadPost(context)
}

export default PostPage
