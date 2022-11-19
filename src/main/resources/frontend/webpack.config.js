const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const webpack = require('webpack');

module.exports = {
    mode: 'development',
    entry: {
        panel: './src/panel/panel.js',
        index: './src/main/index.js',
        login: './src/login/login.js',
        error: './src/error/error.js',
        indexObserver: './src/observers/indexObserver.js',
        newsPageObserver: "./src/observers/newsPageObserver.js",
        documentPageObserver: "./src/observers/documentPageObserver.js",
        albumPageObserver: "./src/observers/albumPageObserver.js",
        articlePageObserver: "./src/observers/articlePageObserver.js",
        allNewsPageObserver: "./src/observers/allNewsPageObserver.js"
    },
    output: {
        filename: 'scripts/[name].js',
        path: path.resolve(__dirname, '../static/'),
        assetModuleFilename: 'assets/[name][ext]'
    },
    resolve: {
        alias: {
            jQuery: path.resolve(__dirname, 'node_modules/jquery')
        }
    },
    module: {
        rules: [
            {
                test: /\.(sa|sc|c)ss$/,
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {
                            publicPath: "../"
                        },
                    },
                    {
                        loader: 'css-loader',
                        options: {
                            sourceMap: true
                        }
                    },
                    "postcss-loader",
                    "sass-loader",
                ],
            },
        ],
    },
    optimization: {
        minimizer: [
            `...`,
            new CssMinimizerPlugin()
        ]
    }
    ,
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        }),
        new MiniCssExtractPlugin({
            filename: 'styles/[name].css'
        })
    ]
}
