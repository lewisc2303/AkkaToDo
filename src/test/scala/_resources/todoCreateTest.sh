#!/usr/bin/env bash

curl -d '{
    "item": {
      "title": "Go Shopping",
      "description": "get some potatoes",
      "deadline": "2019-09-28"
    },
    "dateCreated": "2019-09-28",
    "urgency": {
      "MaybeLater": {}
    }
  }' -H "Content-Type: application/json" -XPOST http://localhost:8080/todos/create
