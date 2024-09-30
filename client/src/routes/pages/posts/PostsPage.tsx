import { FC, useEffect, useState } from 'react'
import { toast } from 'react-toastify'
import PostsList from '../../../ui/posts/PostsList'
import { PagePostDto } from '../../../api/codegen'
import { postsApi } from '../../../api/api'
import Paginator from '../../../ui/core/Paginator'
import { useParams } from 'react-router-dom'

const PostsPage: FC = () => {
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
            <Paginator page={page.page} totalPages={page.totalPages} />
        </>
    )
}

export default PostsPage
