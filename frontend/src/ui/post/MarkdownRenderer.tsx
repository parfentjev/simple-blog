import { FC } from 'react'
import { ReactMarkdown } from 'react-markdown/lib/react-markdown'

const MarkdownRenderer: FC<{ text: string }> = ({ text }) => {
  return <ReactMarkdown linkTarget={'_blank'}>{text}</ReactMarkdown>
}

export default MarkdownRenderer
