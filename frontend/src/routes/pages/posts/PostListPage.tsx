import { FC, useEffect, useState } from 'react'
import { toast } from 'react-toastify'
import PostsList from '../../../components/posts/PostsList'
import { PagePostDto } from '../../../../codegen'
import { postsApi } from '../../../api/api'
import Paginator from '../../../components/core/Paginator'
import { useParams } from 'react-router'

const PostListPage: FC = () => {
    const { page: pageNumber } = useParams()
    const [page, setPage] = useState<PagePostDto>()

    useEffect(() => {
        postsApi()
            .postsPublishedGet({ page: Number(pageNumber || 1) })
            .then((page) => setPage(page))
            .catch(() => toast.error('Failed to laod posts.'))
    }, [pageNumber])

    if (!page || page.items.length === 0) {
        return <p className="text-center">There are no posts yet!</p>
    }

    return (
        <>
            <PostsList posts={page.items} />
            <Paginator
                page={page.page}
                totalPages={page.totalPages}
                urlPath="/posts/"
            />
        </>
    )
}

export default PostListPage
