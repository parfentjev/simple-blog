import { FC, useEffect, useState } from 'react'
import { PagePostDto } from '../../../../codegen'
import { postsApi } from '../../../api/api'
import { useAuthContext } from '../../../store/auth-context'
import { toast } from 'react-toastify'
import EditorPostsList from '../../../components/admin/editor/EditorPostsList'

const EditorPostListPage: FC = () => {
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

export default EditorPostListPage
