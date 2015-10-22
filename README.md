Usage:

```java
NiftyNotificationView
    .build(this, "Hello world!", Effects.slideIn, R.id.container)
    .setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO onClick()
        }
    })
    .show();
```
