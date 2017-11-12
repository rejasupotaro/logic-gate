import tensorflow as tf

if __name__ == '__main__':
    input = [[0, 0], [0, 1], [1, 0], [1, 1]]
    output = [[0], [0], [0], [1]]

    x = tf.placeholder(tf.float32, [None, 2])
    y = tf.placeholder(tf.float32, [None, 1])

    weight = tf.Variable(tf.random_uniform([2, 1], -0.1, 1.0))
    bias = tf.Variable(tf.zeros([1]))
    y_pred = tf.nn.relu(tf.matmul(x, weight) + bias)

    cross_entropy = tf.square(y - y_pred)
    loss = tf.reduce_mean(cross_entropy)
    optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.1)
    train_step = optimizer.minimize(loss)

    tf.summary.scalar('loss', loss)
    merged = tf.summary.merge_all()

    with tf.Session() as session:
        session.run(tf.global_variables_initializer())
        writer = tf.summary.FileWriter('logs', session.graph)

        for step in range(1001):
            _, summary, l = session.run(
                [train_step, merged, loss],
                feed_dict={x: input, y: output}
            )

            if step % 100 == 0:
                print("step: {0}, loss: {1}".format(step, l))
                print("w: {0}, b: {1}".format(weight.eval(), bias.eval()))
                writer.add_summary(summary, step)

        for data in input:
            print("input: {} | output: {}".format(data, session.run(y_pred, feed_dict={x: [data]})))
