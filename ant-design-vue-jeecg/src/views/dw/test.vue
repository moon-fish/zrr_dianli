<template>
  <a-card>
    <a-row >
      <a-col :span="6" style="background-color: yellow">
        col-12 col-offset-6
      </a-col>
      <a-col :span="12" :offset="6" style="background-color: yellow">
        col-12 col-offset-6
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
  export default {
    name: 'test'
  }
</script>

<style scoped>

</style>