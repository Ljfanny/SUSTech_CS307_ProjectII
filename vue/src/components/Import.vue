<template>
  <div>
    <el-container>
      <el-header> </el-header>
      <el-header> CS307 ProjectII </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
            <el-button type="primary" plain @click="backToHome">Home</el-button>
          </el-col>
          <el-col :span="2">
            <!-- <el-upload
              ref="upload"
              action
              :limit="1"
              :file-list="fileList"
              :auto-upload="false"
              :http-request="httpRequest"
            >
              <el-button type="primary">Upload</el-button>
            </el-upload> -->
            <input  type="file" id="files" ref="refFile" v-on:change="importCsv">
          </el-col>
        </el-row>
      </el-header>
    </el-container>
  </div>
</template>

<script>
import Papa from 'papaparse'
export default {
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    importCsv () {
      let selectedFile = null
      selectedFile = this.$refs.refFile.files[0]
      if (selectedFile === undefined) {
        return
      }
      var reader = new FileReader()
      reader.readAsDataURL(selectedFile)
      reader.onload = evt => {
        Papa.parse(selectedFile, {
          complete: res => {
            let data = res.data
            if (data[data.length - 1] === '') {
              data.pop()
            }
            for (var i = 0; i < data.length; i++) {
              let parts = data[i]
              this.$axios
                .post('/inventories', {
                  id: parseInt(parts[0]),
                  supplyCenter: parts[1],
                  productModel: parts[2],
                  supplyStaff: parts[3],
                  date: Date.parse(parts[4]),
                  purchasePrice: parseInt(parts[5]),
                  totalQuantity: parseInt(parts[6])
                })
            }
          }
        })
      }
    }
  },
  mounted () {}
}
</script>

<style>
.el-header {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 400px;
}

body > .el-container {
  margin-bottom: 40px;
}
</style>
