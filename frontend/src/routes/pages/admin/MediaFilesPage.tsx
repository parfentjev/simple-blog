import { FC } from 'react'
import MediaUploadingForm from '../../../ui/admin/editor/MediaUploadingForm'

const MediaFilesPage: FC = () => {
    return (
        <div>
            <h1>Upload</h1>
            <MediaUploadingForm
                urlPrefix={process.env.REACT_APP_MEDIA_URL || ''}
            />
        </div>
    )
}

export default MediaFilesPage
