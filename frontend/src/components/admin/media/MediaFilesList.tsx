import { FC } from 'react'
import { MediaDto } from '../../../../codegen'

interface MediaFilesListProps {
  files: MediaDto[]
}

const MediaFilesList: FC<MediaFilesListProps> = (props) => {
  return (
    <table>
      <thead>
        <tr>
          <th>id</th>
          <th>originalFilename</th>
          <th>date</th>
        </tr>
      </thead>
      <tbody>
        {props.files.map((file) => (
          <tr key={file.id}>
            <td>
              <a href={import.meta.env.VITE_MEDIA_URL + file.id} target="_blank" rel="noreferrer">
                {file.id}
              </a>
            </td>
            <td>{file.originalFilename}</td>
            <td>{file.date.toJSON()}</td>
          </tr>
        ))}
      </tbody>
    </table>
  )
}

export default MediaFilesList
