import PageDto from '../PageDto'
import PostPreviewDto from '../PostPreviewDto'
import Response from './Resonse'

type GetPostsResponse = PageDto<PostPreviewDto> & Response

export default GetPostsResponse
