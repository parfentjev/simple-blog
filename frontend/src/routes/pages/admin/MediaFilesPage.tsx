import { FC, useEffect, useState } from 'react'
import MediaUploadingForm from '../../../ui/admin/media/MediaUploadingForm'
import MediaFilesList from '../../../ui/admin/media/MediaFilesList'
import { useParams } from 'react-router-dom'
import { PageMediaDto } from '../../../api/codegen'
import { mediaApi } from '../../../api/api'
import { useAuthContext } from '../../../store/auth-context'
import { toast } from 'react-toastify'
import Paginator from '../../../ui/core/Paginator'

const MediaFilesPage: FC = () => {
    const { token } = useAuthContext()

    const { page: pageNumber } = useParams()
    const [page, setPage] = useState<PageMediaDto>()

    useEffect(() => {
        mediaApi(token)
            .mediaGet({ page: Number(pageNumber || 1) })
            .then((page) => setPage(page))
            .catch(() => toast.error('Failed to laod media files.'))
    }, [pageNumber, token])

    if (!page || page.items.length === 0) {
        return <p className="text-center">There are no media files yet!</p>
    }

    return (
        <div>
            <h1>Media Files</h1>
            <h2>Upload</h2>
            <MediaUploadingForm />
            <h2>List</h2>
            <MediaFilesList files={page.items} />
            <Paginator
                page={page.page}
                totalPages={page.totalPages}
                urlPath="/admin/media/"
            />
        </div>
    )
}

export default MediaFilesPage
