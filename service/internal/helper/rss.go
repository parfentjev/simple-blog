package helper

import (
	"fmt"
	"time"

	"github.com/gorilla/feeds"
	"github.com/parfentjev/simple-blog/internal/db"
)

const baseUrl string = "https://fakeplastictrees.ee"

func GenerateRss(posts []db.SelectVisiblePostsRow) (string, error) {
	feed := &feeds.Feed{
		Title:       "Fake Plastic Trees",
		Link:        &feeds.Link{Href: baseUrl},
		Description: "A simple blog about technical topics, life and other things.",
		Created:     time.Now(),
	}

	for _, p := range posts {
		feed.Items = append(feed.Items, &feeds.Item{
			Id:          p.ID,
			Title:       p.Title,
			Description: p.Summary,
			Link:        &feeds.Link{Href: fmt.Sprintf("%v/post/%v", baseUrl, p.ID)},
			Created:     p.Date,
		})
	}

	return feed.ToAtom()
}
