# Bookmark Manager

## TODO: (☐/☑)
☐ Create Objects for bookmarks
☐ Create data saving/loading
☐ Add ability to scrape bookmark title from the internet

## Data Structure
- Our data will be stored in json files 
- Each URL will have a topic/category associated (if no topic is specified a default one will be assigned)
- Each URL will also have a title associated with it. This can be set by the user or autofilled by the program (html title)

Example json:
```
[{
	"topic": "default",
	"url": "https://google.com",
	"title": "google"
}, {
	"topic": "coding",
	"url": "https://github.com",
	"title": "github"
}]
```