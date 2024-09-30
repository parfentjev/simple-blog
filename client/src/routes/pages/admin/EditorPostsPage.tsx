import { FC, useEffect, useState } from 'react'
import { PagePostDto } from '../../../api/codegen'
import { postsApi } from '../../../api/api'
import { useAuthContext } from '../../../store/auth-context'
import { toast } from 'react-toastify'
import EditorPostsList from '../../../ui/admin/EditorPostsList'

const EditorPostsPage: FC = () => {
    const { token } = useAuthContext()
    const [page, setPage] = useState<PagePostDto>()

    useEffect(() => {
        postsApi(token)
            .postsEditorGet()
            .then((page) => setPage(page))
            .catch(() => toast.error('Failed to laod posts.'))
    }, [token])

    if (!page || page.items.length === 0) {
        return <p className="text-center">There are no posts yet!</p>
    }

    return <EditorPostsList posts={page.items} />
}

export default EditorPostsPage
