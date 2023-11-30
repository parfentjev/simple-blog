import Container from '@/ui/layout/Container'
import SearchBox from '@/ui/search/SearchBox'
import { FC } from 'react'

const SearchPage: FC = () => {
  return (
    <Container name='Search' centered={true}>
      <SearchBox />
    </Container>
  )
}

export default SearchPage
