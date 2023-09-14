class Post {
    id: string
    title: string
    summary: string
    text: string
    date: string
  
    constructor(data: Post) {
      this.id = data.id
      this.title = data.title
      this.summary = data.summary
      this.text = data.text
      this.date = data.date
    }
  }
  
  export default Post
  