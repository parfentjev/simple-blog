class PostPreview {
  id: string
  title: string
  summary: string
  date: string

  constructor(data: PostPreview) {
    console.log('test')
    this.id = data.id
    this.title = data.title
    this.summary = data.summary
    this.date = data.date
  }
}

export default PostPreview
