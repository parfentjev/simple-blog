import { getPostById } from '@/api/api-executor'
import Post from '@/ui/post/Post'
import { GetServerSideProps } from 'next'
import { FC } from 'react'
import { toast } from 'react-toastify'
import NotFoundErrorPage from '../../404'
import GetPostByIdResponse from '@/api/models/response/GetPostByIdResponse'
import PostPage, { loadPost } from '.'

type PostPageProps = {
  post: GetPostByIdResponse
}

const TitledPostPage: FC<PostPageProps> = ({ post }) => {
  return <PostPage post={post} />
}

export const getServerSideProps: GetServerSideProps<PostPageProps> = async (
  context,
) => {
  return await loadPost(context)
}

export default TitledPostPage
